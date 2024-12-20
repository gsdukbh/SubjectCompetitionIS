import Vue from 'vue'
import Clipboard from 'clipboard'
Vue.prototype.clipboard = Clipboard;
function clipboardSuccess() {
  Vue.prototype.$message({
    message: '复制成功',
    type: 'success',
    duration: 1500
  })
}

function clipboardError() {
  Vue.prototype.$message({
    message: '失败',
    type: 'error'
  })
}

export default function handleClipboard(text, event) {
  const clipboard = new Clipboard(event.target, {
    text: () => text
  });
  clipboard.on('success', () => {
    clipboardSuccess();
    clipboard.destroy()
  });
  clipboard.on('error', () => {
    clipboardError();
    clipboard.destroy()
  });
  clipboard.onClick(event)
}
