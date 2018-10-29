const connection = require('../../package.json').connection;

export const environment = {
  production: true,
  connection: connection,
  preventLoadParams: true
};
