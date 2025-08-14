pragma solidity >=0.5.0;

// SPDX-License-Identifier: MIT

// Solidity does not have float / double types, so we use uint256 for fixed-point arithmetic

contract Tether {
    string public name = "Mock Tether USD";
    string public symbol = "mUSDT";
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

    // The approve function is vital for allowing other addresses (spenders) to transfer tokens on behalf of the token owner
    function approve(address _spender, uint256 _value) public returns (bool success) {
        require(_spender != address(0), "Invalid address");

        allowance[msg.sender][_spender] = _value; // Set the allowance for the spender
        emit Approval(msg.sender, _spender, _value); // Emit approval event
        return true;
    }   

    // allow another contract or address to spend tokens on behalf of the owner
    // transfer value from _from to _to address
    // this function is used by the spender to transfer tokens from the owner's balance
    /*
         suppose a bank wants to transfer 100 tokens from A to B on behalf of A.
         allowance of A to bank must be at least 100 tokens. (i.e. allowance[A][bank] >= 100)
         then the bank can call transferFrom(A, B, 100) to transfer 100 tokens from A to B.
    */
    function transferFrom(address _from, address _to, uint256 _value) public returns (bool success) {
        // _from address actually has the tokens.
        require(balanceOf[_from] >= _value, "Insufficient balance");

        // the msg.sender (the spender) has been approved to spend that amount from the _from address
        require(allowance[_from][msg.sender] >= _value, "Allowance exceeded");
        require(_to != address(0), "Invalid address");

        balanceOf[_from] -= _value; // Deduct from the original owner's balance
        balanceOf[_to] += _value; // Add to recipient's balance

        allowance[_from][msg.sender] -= _value; // Deduct from allowance granted to spender
        emit Transfer(_from, _to, _value); // Emit transfer event
        return true;
    }
}