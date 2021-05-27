package me.spring.GroovyDemo.store

import me.spring.GroovyDemo.AppConstants
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
@EnableElasticsearchRepositories(basePackages = "me.spring.GroovyDemo")
@ComponentScan(basePackages = ["me.spring.GroovyDemo"])
class ElasticsearchClientConfig {
    @Bean
    RestHighLevelClient client() {
        ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo(AppConstants.ELASTIC_SERVER)
                .build()

        return RestClients.create(clientConfiguration).rest()
    }

    @Bean
    ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client())
    }
}
