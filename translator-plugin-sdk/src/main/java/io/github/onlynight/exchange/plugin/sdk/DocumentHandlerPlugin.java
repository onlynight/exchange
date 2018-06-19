package io.github.onlynight.exchange.plugin.sdk;

import java.util.List;

public interface DocumentHandlerPlugin<T> {

    String TEXT_TYPE_ANDROID = "android";
    String TEXT_TYPE_IOS = "ios";
    String TEXT_TYPE_CH_TEXT = "ch-text";

    /**
     * return typed text type
     *
     * @return typed text
     */
    String typedTextType();

    String getResultFolderName(String targetLanguage);

    List<TranslatorDocument<T>> getDocument();

    TranslatorDocument<T> convertDocument(T document, String path);

    void writeNewDocument(TranslatorDocument<T> document, String targetLanguage);

    class TranslatorDocument<T> {

        private String path;

        /**
         * source document
         */
        private T sourceDocument;

        /**
         * source document translate source line
         */
        private List<String> srcStrings;

        /**
         * translated lines
         */
        private List<String> destStrings;

        /**
         * @param path           doc source path
         * @param sourceDocument source document translate source line
         * @param srcStrings     translated lines
         */
        public TranslatorDocument(String path, T sourceDocument, List<String> srcStrings) {
            this.path = path;
            this.sourceDocument = sourceDocument;
            this.srcStrings = srcStrings;
        }

        public List<String> getSrcStrings() {
            return srcStrings;
        }

        public void setSrcStrings(List<String> srcStrings) {
            this.srcStrings = srcStrings;
        }

        public List<String> getDestStrings() {
            return destStrings;
        }

        public void setDestStrings(List<String> destStrings) {
            this.destStrings = destStrings;
        }

        public T getSourceDocument() {
            return sourceDocument;
        }

        public void setSourceDocument(T sourceDocument) {
            this.sourceDocument = sourceDocument;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

}
