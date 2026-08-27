package com.xiaoguo.guaiagent.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.List;

/**
 * 基于 LangChain4j 的本地 RAG 知识库。
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "langchain4j", name = "enabled", havingValue = "true")
public class LangChain4jRagConfiguration {

    @Bean
    public EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    public EmbeddingStore<TextSegment> loveEmbeddingStore(EmbeddingModel embeddingModel) {
        Path documentDirectory = Path.of("src", "main", "resources", "document");
        List<Document> documents = FileSystemDocumentLoader.loadDocuments(documentDirectory);
        DocumentSplitter splitter = DocumentSplitters.recursive(500, 80);
        EmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(store)
                .build()
                .ingest(documents);
        return store;
    }

    @Bean
    public ContentRetriever loveContentRetriever(EmbeddingStore<TextSegment> loveEmbeddingStore,
                                                  EmbeddingModel embeddingModel) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(loveEmbeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.55)
                .build();
    }
}
