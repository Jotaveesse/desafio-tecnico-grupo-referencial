import { Component, signal } from '@angular/core';
import { Upload } from './upload/upload';

@Component({
  imports: [Upload],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('frontend');
}
