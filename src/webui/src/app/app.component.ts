import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(public translateService: TranslateService,
              private titleService: Title) {
    this.translateService.addLangs(['tr', 'en']);
    let selectedLanguage = localStorage.getItem('selectedLanguage');
    if (selectedLanguage == null) {
      const browserLang = this.translateService.getBrowserLang();
      const languageToUse = browserLang.match(/tr|en/) ? browserLang : 'tr';
      localStorage.setItem('selectedLanguage', languageToUse);
      selectedLanguage = languageToUse;
    }
    translateService.setDefaultLang(selectedLanguage);
    translateService.use(selectedLanguage);
  }

  ngOnInit() {
    this.translateService.get('BROWSER.title').subscribe((res: string) => {
      this.titleService.setTitle(res);
    });
  }
}
