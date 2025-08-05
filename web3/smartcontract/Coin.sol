pragma solidity >=0.8.2 <0.9.0;

/*
   only creator can create coins.
   anyone can transfer coins without any registration.
*/
contract Coin {
  address public minter;
  mapping (address => uint) public balances;

  /*
    event allow clients to react to specific contract changes you declare
    these are logs stored in the chain.
    events are a mechanism for smart contracts to communicate with external applications and other
    contracts by emitting log records to the Ethereum Virtual Machine (EVM). These logs are stored as
     part of the transaction receipt on the blockchain and are accessible to external entities.
  */
  event Sent(address from, address to, uint amount);

  constructor() {
     minter = msg.sender;
  }

  // make new coins and send them to address
  // only owner can run this function
  function mint(address receiver, uint amount) public {
     require(msg.sender == minter);
     balances[receiver] += amount;

  }

   // error is the type
   error insufficientBalance(uint requested, uint available);

  // send any amount of coins to an existing address
  function send(address receiver, uint amount) public {

    // require(balances[msg.sender] >= amount, "Insufficient balance.");

    // alternative way to perform check and throw error by revert
    if (amount > balances[msg.sender]) {
          revert insufficientBalance({
            requested: amount,
            available: balances[msg.sender]
          });
    }

    balances[msg.sender] -= amount;
    balances[receiver] += amount;
    emit Sent(msg.sender, receiver, amount);
  }

}