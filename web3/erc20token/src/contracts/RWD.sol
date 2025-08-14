pragma solidity >=0.5.0;

// SPDX-License-Identifier: MIT

// Solidity does not have float / double types, so we use uint256 for fixed-point arithmetic

contract RWD {
    string public name = "Reward Token";
    string public symbol = "RWD";
    uint8 public decimals = 18; // Tether uses 6 decimals, but we use 18 for compatibility with ERC20 standards

    uint256 public totalSupply = 1000000000000000000000000; // Total supply in wei (18 decimals)
 
    // indexed allows filtering by address
    event Transfer( 
        address indexed _from,  
        address indexed _to, 
        uint256 _value
    );

    event Approval(
        address indexed _owner,
        address indexed _spender,
        uint256 _value
    );
    mapping(address => uint256) public balanceOf; // Maps addresses to their balances

    mapping(address => mapping(address => uint256)) public allowance; // Maps owner address to spender address to amount allowed

    constructor() public {
        balanceOf[msg.sender] = totalSupply; // Assign the total supply to the contract creator
    }

    function transfer(address _to, uint256 _value) public returns (bool success) {
        // require(_to != address(0), "Invalid address");
        require(balanceOf[msg.sender] >= _value, "Insufficient balance");

        balanceOf[msg.sender] -= _value; // Deduct from sender's balance
        balanceOf[_to] += _value; // Add to recipient's balance

        emit Transfer(msg.sender, _to, _value); // Emit transfer event
        return true;
    }

    function approve(address _spender, uint256 _value) public returns (bool success) {
        require(_spender != address(0), "Invalid address");

        allowance[msg.sender][_spender] = _value; // Set the allowance for the spender
        emit Approval(msg.sender, _spender, _value); // Emit approval event
        return true;
    }   

    // allow another contract or address to spend tokens on behalf of the owner
    function transferFrom(address _from, address _to, uint256 _value) public returns (bool success) {
        require(balanceOf[_from] >= _value, "Insufficient balance");
        require(allowance[_from][msg.sender] >= _value, "Allowance exceeded");
        require(_to != address(0), "Invalid address");

        balanceOf[_from] -= _value; // Deduct from sender's balance
        balanceOf[_to] += _value; // Add to recipient's balance

        allowance[_from][msg.sender] -= _value; // Deduct from allowance
        emit Transfer(_from, _to, _value); // Emit transfer event
        return true;
    }
}