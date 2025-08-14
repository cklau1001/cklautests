pragma solidity >=0.5.0;

// SPDX-License-Identifier: MIT

contract Migrations {
  address public owner;
  uint public last_completed_migration;  // keeps track of the last completed migration

  modifier restricted() {
    require(msg.sender == owner, "Only the owner can call this function");
    _;
  }

  constructor() public {
    owner = msg.sender;
  }

   /*
     truffle searches for setCompleted() in the migrations contract
     and calls it to update the last_completed_migration variable.
   */
  function setCompleted(uint completed) public restricted {
    last_completed_migration = completed;
  }

  function upgrade(address new_address) public restricted {
    Migrations upgraded = Migrations(new_address);
    upgraded.setCompleted(last_completed_migration);
  }
}