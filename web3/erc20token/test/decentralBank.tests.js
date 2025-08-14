const Tether = artifacts.require('Tether');
const RWD = artifacts.require('RWD');
const DecentralBank = artifacts.require('DecentralBank');

require('chai').use(require('chai-as-promised')).should();

contract ('DecentralBank', ([owner, customer]) => {
    let tether, rwd, decentralBank;

    function tokens(number) {
        return web3.utils.toWei(number, 'ether'); // Convert to wei
    }

    before(async () => {
        tether = await Tether.new();
        rwd = await RWD.new();
        decentralBank = await DecentralBank.new(rwd.address, tether.address);

        // Transfer all RWD tokens to DecentralBank (1 million tokens)
        await rwd.transfer(decentralBank.address, tokens('1000000')); // 1 million RWD tokens
        console.log("RWD tokens transferred to DecentralBank");

        // Distribute 100 Tether tokens to investor
        await tether.transfer(customer, tokens('100'), {from: owner}); // 100 Tether tokens

    });

    /*
      Test cases are running in async mode. We need to use await that ensure that the variable assignment is completed before moving to the next line.
      For example, const name = await decentralBank.name(); will wait for the name to be fetched before proceeding.
      Without await, the test will not wait for the promise to resolve and will throw an error.
    */
    describe('DecentralBank deployment', async () => {
        it('has a name', async () => {
            const name = await decentralBank.name();
            assert.equal(name, 'Decentral Bank');
        });

        it('contract has tokens', async () => {
            let balance = await rwd.balanceOf(decentralBank.address)
            assert.equal(balance, tokens('1000000'))
        });

        describe('Yield Farming', async () => {
            it('rewards tokens for staking', async () => {
                let result

                // Check Investor Balance
                result = await tether.balanceOf(customer)
                assert.equal(result.toString(), tokens('100'), 'customer mock wallet balance before staking')

                // Check Staking For Customer of 100 tokens
                /*
                  each entity (e.g customer) has a mock wallet in Tether contract.
                  Before customer can deposit to DecentralBank, customer must approve the transfer of tokens 
                  from their mock wallet to DecentralBank.

                  After approval, allowance of msg.sender to DecentralBank is set to 100 tokens.
                    allowance[msg.sender][decentralBank.address] = 100 tokens 
                       where msg.sender is customer mock wallet
                  depositTokens() invoke transferFrom() function in Tether contract.
                  transferFrom() function will transfer 100 tokens from customer mock wallet 
                  to DecentralBank mock, i.e.
                      in Tether contract, balanceOf[customer] -= 100 tokens
                      in Tether contract, balanceOf[decentralBank] += 100 tokens
                  at the same time, allowance[msg.sender][decentralBank.address] is reduced by 100 tokens.    
                  when decentralBank calls Tether.transferFrom , _from is customer mock wallet and msg.sender is decentralBank mock wallet itself.
                  that is why
                    allowance[_from][msg.sender] -= _value;
                */
                await tether.approve(decentralBank.address, tokens('100'), {from: customer})
                await decentralBank.depositTokens(tokens('100'), {from: customer})

                // Check Updated Balance of Customer
                // since customer deposited 100 tokens, the balance should be 0
                result = await tether.balanceOf(customer)
                assert.equal(result.toString(), tokens('0'), 'customer mock wallet balance after staking 100 tokens')     

                // Check Updated Balance of Decentral Bank
                result = await tether.balanceOf(decentralBank.address)
                assert.equal(result.toString(), tokens('100'), 'decentral bank mock wallet balance after staking from customer')     

                // Is Staking Update
                result = await decentralBank.isStaking(customer)
                assert.equal(result.toString(), 'true', 'customer is staking status after staking')

                // Issue Tokens
                await decentralBank.issueTokens({from: owner})

                // Ensure Only The Owner Can Issue Tokens
                await decentralBank.issueTokens({from: customer}).should.be.rejected;

                // Unstake Tokens
                await decentralBank.unstakeTokens({from: customer})

                // Check Unstaking Balances
                result = await tether.balanceOf(customer)
                assert.equal(result.toString(), tokens('100'), 'customer mock wallet balance after unstaking')     
            
                // Check Updated Balance of Decentral Bank
                result = await tether.balanceOf(decentralBank.address)
                assert.equal(result.toString(), tokens('0'), 'decentral bank mock wallet balance after staking from customer')     
            
                // Is Staking Update
                result = await decentralBank.isStaking(customer)
                assert.equal(result.toString(), 'false', 'customer is no longer staking after unstaking')

                // Is stakingBalance updated
                result = await decentralBank.stakingBalance(customer)
                assert.equal(result.toString(), tokens('0'), 'customer staking balance after unstaking')

            });
        });

    });

    describe('Rewards Token deployment', async () => {
        it('has a name', async () => {
            const name = await rwd.name();
            assert.equal(name, 'Reward Token');
        });
    });

    describe('Tether Token deployment', async () => {
        it('has a name', async () => {
            const name = await tether.name();
            assert.equal(name, 'Mock Tether USD');
        });
    });
});