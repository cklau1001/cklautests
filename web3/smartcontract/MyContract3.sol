pragma solidity >=0.8.2 <0.9.0;

contract MyContract3 {

    // address - a special type that tells the address of an entity in a chain
    //           20-byte ethereum address

    // state variable - values permanently stored in the contract storage, can be accessed throughout functions

    address owner;
    uint    fortune;
    bool    deceased;

    uint public changeValue;
    string public tom = "tom";

    // constructor - special function that is triggered when deploying a contract
    // payable - allow constructor to send / receive ether
    //           payable is crucial because it signifies that these addresses are capable of
    //           receiving Ether. Without payable, the contract wouldn't be able to transfer funds to them,
    //           which defeats the purpose of an inheritance contract.
    // msg - message, msg.sender - party that calls this contract
    // msg.value -

    constructor() payable {
         owner = msg.sender;
         fortune = msg.value;  // how much ether to be sent
         deceased = false;
    }


   // modifier - update variables

   // only contract owner can invoke this contract
   modifier onlyOwner {
     require(msg.sender == owner, "Only owner can call this function");
     _;     // proceed with the contract if this is the owner of this contract
   }

   modifier mustBeDeceased {
     require(deceased == true, "Contract can only be triggered after deceased is true");
     _;
   }

   // array to store addresses who can receive ether from this contract
   // payable means that the addresses in this array can receive / send ether
   address payable[] familyWallets;

   // a map of address to fortune
   mapping(address => uint) inheritance;

   function setInheritance(address payable  wallet, uint amount) public onlyOwner {
       inheritance[wallet] = amount;
       familyWallets.push(wallet);
   }

   // pay each family member based on wallet address
   // mustBeDeceased is the defined modifier so that this function can only be invoked if that modifier satisfies
   // private - cannot be run by anyone, only by setDeceasedToTrue()
   function payout() private mustBeDeceased {
      for (uint i=0; i < familyWallets.length; i++) {

        // transfer ether to this wallet by using transfer() function
        // amount is the value insider inheritance for that address, i.e. inheritance[familyWallets[i]]
           familyWallets[i].transfer(inheritance[familyWallets[i]]);
      }
   }

   // oracle switch simulation, i.e. some external source trigger this function
   function hasDeceased() public onlyOwner {
      deceased = true;
      payout();

   }

   /*
      how to return multiple values in a function
   */
   function f() public pure returns(uint, bool, string memory) {
      return (3, true, "Hello");
   }

   function g() public {
      (changeValue, ,tom ) = f();    // destructuring to get 3 and assign to changeValue state variable
   }

}