const proxy = [
    {
      context: '/famec',
      target: 'http://localhost:8080',
      pathRewrite: {'' : ''}
    }
  ];
  
  module.exports = proxy;