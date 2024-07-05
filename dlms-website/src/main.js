import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'

const ro = new ResizeObserver(entries => {
    for (let entry of entries) {
        const cr = entry.contentRect;
        console.log('Element:', entry.target);
        console.log(`Element size: ${cr.width}px x ${cr.height}px`);
        console.log(`Element padding: ${cr.top}px ; ${cr.left}px`);
    }
});
ResizeObserver.prototype.disconnect = ro.disconnect;
ResizeObserver.prototype.observe = ro.observe;
ResizeObserver.prototype.unobserve = ro.unobserve;

createApp(App).use(router).use(store).use(ElementPlus).mount('#app');
