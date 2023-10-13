package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.S3Configuration
import java.time.Duration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentials

@Configuration
class S3Config(val s3props: S3ClientConfigurationProperties) {

    @Bean
    fun asyncS3client(
        credentialsProvider: AwsCredentialsProvider,
    ): S3AsyncClient {
        val httpClient =
            NettyNioAsyncHttpClient.builder()
                .writeTimeout(Duration.ZERO)
                .maxConcurrency(64)
                .build()

        val serviceConfiguration =
            S3Configuration.builder()
                .checksumValidationEnabled(false)
                .chunkedEncodingEnabled(true)
                .build()

        var asyncS3Client =
            S3AsyncClient.builder().httpClient(httpClient)
                .region(s3props.region)
                .credentialsProvider(credentialsProvider)
                .serviceConfiguration(serviceConfiguration)

        if(s3props.endpoint != null) {
            asyncS3Client = asyncS3Client.endpointOverride(s3props.endpoint)
        }

        return asyncS3Client.build()
    }

    @Bean
    fun awsCredentialsProvider(): AwsCredentials {
        return AwsBasicCredentials.create(
                s3props.accessKeyId,
                s3props.secretAccessKey
            )
    }
}
