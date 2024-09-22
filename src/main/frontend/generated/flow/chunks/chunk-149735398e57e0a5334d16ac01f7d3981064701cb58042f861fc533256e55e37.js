import '@vaadin/side-nav/src/vaadin-side-nav.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/app-layout/src/vaadin-app-layout.js';
import '@vaadin/tooltip/src/vaadin-tooltip.js';
import '@vaadin/app-layout/src/vaadin-drawer-toggle.js';
import '@vaadin/icon/src/vaadin-icon.js';
import 'Frontend/generated/jar-resources/com/github/appreciated/apexcharts/apexcharts-wrapper.ts';
import '@vaadin/side-nav/src/vaadin-side-nav-item.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/button/src/vaadin-button.js';
import 'Frontend/generated/jar-resources/buttonFunctions.js';
import { injectGlobalCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { css, unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin';
import $cssFromFile_0 from 'Frontend/generated/jar-resources/com/github/appreciated/apexcharts/apexcharts-wrapper-styles.css?inline';
const $css_0 = typeof $cssFromFile_0  === 'string' ? unsafeCSS($cssFromFile_0) : $cssFromFile_0;
registerStyles('', $css_0, {moduleId: 'apex-charts-style'});