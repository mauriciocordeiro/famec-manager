const proxy = [
    {
      context: '/famec',
      target: 'http://localhost:8080',
      pathRewrite: {'^/famec' : ''}
    }
  ];
  
  module.exports = proxy;