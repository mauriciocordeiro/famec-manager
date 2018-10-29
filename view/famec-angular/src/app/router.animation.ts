import {trigger, state, animate, style, transition} from '@angular/animations';

export function routerTransition() {
  return slideToLeft();
}

function slideToLeft() {
  return trigger('routerTransition', [
    state('void', style({position:'fixed', width:'100%'}) ),
    state('*', style({position:'relative', width:'100%'}) ),
    transition(':enter', [  // before 2.1: transition('void => *', [
      style({'opacity': 0}),
      animate('0.3s ease-in-out', style({'opacity': 1}))
    ]),
    transition(':leave', [  // before 2.1: transition('* => void', [
      style({position:'fixed', 'opacity': 1}),
      animate('0.3s ease-in-out', style({opacity: 0}))
    ])
  ]);
}
