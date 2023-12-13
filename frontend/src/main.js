import { createApp } from 'vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import App from './App.vue';

// import { createVuestic, createIconsConfig} from "vuestic-ui";
// import "vuestic-ui/css";

const app = createApp(App);

// app.use(
//     createVuestic({
//         config: {
//             icons: createIconsConfig({
//                 aliases: [
//                     {
//                         name: "bell",
//                         color: "#FFD43A",
//                         to: "fa4-bell",
//                     },
//                     {
//                         name: "ru",
//                         to: "flag-icon-ru small",
//                     },
//                 ],
//                 fonts: [
//                     {
//                         name: "fa4-{iconName}",
//                         resolve: ({iconName}) => ({class: `fa fa-${iconName}`}),
//                     },
//                     {
//                         name: "flag-icon-{countryCode} {flagSize}",
//                         resolve: ({countryCode, flagSize}) => ({
//                             class: `flag-icon flag-icon-${countryCode} flag-icon-${flagSize}`,
//                         }),
//                     },
//                 ],
//             }),
//             // ...
//         },
//     })
// )

app.use(ElementPlus);
app.config.globalProperties.$apiBaseUrl = 'http://localhost:8082';
// app.config.globalProperties.$apiBaseUrl = 'http://10.26.80.100:8080';
app.mount('#app');
