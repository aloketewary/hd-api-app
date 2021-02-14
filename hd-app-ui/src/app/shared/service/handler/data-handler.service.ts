import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataHandlerService {

  private _availableLanguages: any[];
  private _projectName: string;
  private _showRequiredMarker: boolean;
  private _textAreaMinRows: number;
  private _textAreaMaxRows: number;
  private _tablePageSize: Array<number>;
  private _sidenavMenu: Array<any>;
  private _schoolYear: BehaviorSubject<string>;
  private _schoolName: string;
  private _loginAs: 'teacherapp' | 'adminapp';
  private _isDarkMode: boolean;

  public get schoolName(): string {
    return this._schoolName;
  }
  public set schoolName(value: string) {
    this._schoolName = value;
  }

  constructor() {
    this._schoolYear = new BehaviorSubject<string>(null);
  }

  ngOnDestroy() {
    this._schoolYear.unsubscribe();
  }

  public get schoolYear(): BehaviorSubject<string> {
    return this._schoolYear;
  }
  public set schoolYear(value: BehaviorSubject<string>) {
    this._schoolYear = value;
  }

  public get tablePageSize(): Array<number> {
    return this._tablePageSize;
  }
  public set tablePageSize(value: Array<number>) {
    this._tablePageSize = value;
  }

  public get showRequiredMarker(): boolean {
    return this._showRequiredMarker;
  }
  public set showRequiredMarker(value: boolean) {
    this._showRequiredMarker = value;
  }

  public get projectName(): string {
    return this._projectName;
  }
  public set projectName(value: string) {
    this._projectName = value;
  }

  public get availableLanguages(): any[] {
    return this._availableLanguages;
  }
  public set availableLanguages(value: any[]) {
    this._availableLanguages = value;
  }

  public get textAreaMinRows(): number {
    return this._textAreaMinRows;
  }
  public set textAreaMinRows(value: number) {
    this._textAreaMinRows = value;
  }

  public get textAreaMaxRows(): number {
    return this._textAreaMaxRows;
  }
  public set textAreaMaxRows(value: number) {
    this._textAreaMaxRows = value;
  }

  public get sidenavMenu(): Array<any> {
    return this._sidenavMenu;
  }
  public set sidenavMenu(value: Array<any>) {
    this._sidenavMenu = value;
  }

  public get loginAs(): 'teacherapp' | 'adminapp' {
    return this._loginAs;
  }
  public set loginAs(value: 'teacherapp' | 'adminapp') {
    this._loginAs = value;
  }

  public get isDarkMode(): boolean {
    return this._isDarkMode;
  }
  public set isDarkMode(value: boolean) {
    this._isDarkMode = value;
  }
}
