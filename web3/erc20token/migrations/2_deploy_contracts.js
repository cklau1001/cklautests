const Tether = artifacts.require('Tether');
const RWD = artifacts.require('RWD');
const DecentralBank = artifacts.require('DecentralBank');

module.exports = async function(deployer, network, accounts) {

    const amount = '1000000000000000000000000'; // 1 million Tether tokens

    await deployer.deploy(Tether);
    const tether = await Tether.deployed();

    await deployer.deploy(RWD);
    const rwd = await RWD.deployed();

    await deployer.deploy(DecentralBank, rwd.address, tether.address);        
    const decentralBank = await DecentralBank.deployed();

    // Transfer all Tether tokens to DecentralBank (1 million tokens)
    await rwd.transfer(decentralBank.address, amount); // 1 million RWD tokens
    console.log("RWD tokens transferred to DecentralBank");

    // Distribute 100 Tether tokens to investor
    await tether.transfer(accounts[1], amount); // 100 Tether tokens

};