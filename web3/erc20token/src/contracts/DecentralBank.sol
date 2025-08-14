pragma solidity >=0.5.0;

// SPDX-License-Identifier: MIT

import './RWD.sol';
import './Tether.sol';

contract DecentralBank {

    string public name = "Decentral Bank";
    address public owner;
    Tether public tether;
    RWD public rwd;

    address[] public stakers;

    mapping(address => uint256) public stakingBalance; // Maps addresses to their staking balances
    mapping(address => bool) public isStaking; // Maps addresses to their staking status
    mapping(address => bool) public hasStaked; // Maps addresses to their staking history
    
    constructor(RWD _rwd, Tether _tether) public {
        rwd = _rwd; // Initialize the RWD token contract
        tether = _tether; // Initialize the Tether token contract
        owner = msg.sender; // Set the contract creator as the owner
    }

    // staking function : transfer Tether tokens to this contract (DecentralBank) for staking
    function depositTokens(uint256 _amount) public {
        // Require that the amount is greater than 0
        require(_amount > 0, "Amount must be greater than 0");

        // Require that the user has enough Tether tokens
        // require(tether.balanceOf(msg.sender) >= _amount, "Insufficient Tether balance");

        // Transfer Tether tokens to the DecentralBank contract
        tether.transferFrom(msg.sender, address(this), _amount);

        // Update the staking balance for the user
        stakingBalance[msg.sender] += _amount;

        if(!hasStaked[msg.sender]) {
            stakers.push(msg.sender);
        }

        // Update the staking status        
        isStaking[msg.sender] = true;
        hasStaked[msg.sender] = true;

    }

    // unstaking function : withdraw Tether tokens from this contract (DecentralBank) after staking
    function unstakeTokens() public {
        // Require that the user has staked tokens
        require(isStaking[msg.sender] == true, "You have no staked tokens");

        // Get the staking balance for the user
        uint256 balance = stakingBalance[msg.sender];

        // Require that the user has a positive staking balance
        require(balance > 0, "You have no staked tokens to unstake");

        // Transfer Tether tokens back to the user
        tether.transfer(msg.sender, balance);

        // Reset the user's staking balance
        stakingBalance[msg.sender] = 0;

        // Update the staking status        
        isStaking[msg.sender] = false;
    }  
    

    // issue tokens function : transfer RWD tokens to stakers
    function issueTokens() public {
        // Require that the caller is the owner
        require(msg.sender == owner, "Only the owner can issue tokens");

        for (uint256 i = 0; i < stakers.length; i++) {
            address recipient = stakers[i];
            // uint256 balance = stakingBalance[recipient];
            uint256 balance = stakingBalance[recipient] / 9; // Calculate the balance to be rewarded (1/9 of the staking balance), create incentive to stake more

            if (balance > 0) {
                rwd.transfer(recipient, balance); // Transfer RWD tokens to the staker
            }
        }
    }   
}