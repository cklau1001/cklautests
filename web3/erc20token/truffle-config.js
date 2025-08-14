require('babel-register');
require('babel-polyfill');

module.exports = {
    networks: {
        development: {  
            host: '127.0.0.1',
            port: '7545', // Ganache GUI default port
            network_id: '*', // Match any network id

        },
    },
    contracts_directory: './src/contracts/',
    contracts_build_directory: './src/abis/',
    compilers: {
        solc: {
            version: '^0.5.0', // Specify the Solidity version       
            settings: {     
                optimizer: {
                    enabled: true,
                    runs: 200
                },
            }            
        }
    },
}