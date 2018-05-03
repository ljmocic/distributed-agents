import { Route } from '@angular/router';
import { FrontPageComponent } from './front-page.component';
import { ButtonsComponent } from './body/buttons/buttons.component';
import { RegisterCardComponent } from './body/register-card/register-card.component';
import { LoginCardComponent } from './body/login-card/login-card.component';

export const frontPageRoute: Route = {
    path: '',
    component: FrontPageComponent,
    children: [
        {
            path: 'register',
            component: RegisterCardComponent
        },
        {
            path: 'login',
            component: LoginCardComponent
        },
        {
            path: '',
            component: ButtonsComponent
        }
    ]
};