import { TranslateService } from '@ngx-translate/core';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  constructor(public translate: TranslateService,
              public titleService: Title) {
  }

  ngOnInit() {
  }

  changeLanguage(language: string) {
    this.translate.setDefaultLang(language);
    this.translate.use(language);
    this.translate.get('BROWSER.title').subscribe((res: string) => {
      this.titleService.setTitle(res);
    });
    localStorage.setItem('selectedLanguage', language);

  }
}
