import { EtransitoPage } from './app.po';

describe('etransito App', () => {
  let page: EtransitoPage;

  beforeEach(() => {
    page = new EtransitoPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
