
import codeSyntaxHightlight from '@toast-ui/editor-plugin-code-syntax-highlight';
import highlight from 'highlight.js';
import chart from '@toast-ui/editor-plugin-chart';
export default {
  minHeight: '200px',
  previewStyle: 'vertical',
  useCommandShortcut: true,
  useDefaultHTMLSanitizer: true,
  usageStatistics: false,
  hideModeSwitch: false,
  useCustomSyntax: true,
  language: 'zh_CN',
  toolbarItems: [
    'heading',
    'bold',
    'italic',
    'strike',
    'divider',
    'hr',
    'quote',
    'divider',
    'ul',
    'ol',
    'task',
    'indent',
    'outdent',
    'divider',
    'table',
    // 'image',
    'link',
    'divider',
    'code',
    'codeblock',
  ],

  plugins: [[codeSyntaxHightlight, { highlight }],[chart]]
}
