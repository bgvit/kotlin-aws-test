package com.example.demo

import org.springframework.boot.context.properties.ConfigurationProperties
import software.amazon.awssdk.regions.Region
import java.net.URI

@ConfigurationProperties(prefix = "aws.s3")
data class S3ClientConfigurationProperties(
    var region: Region = Region.US_EAST_1,
    var endpoint: URI?,
    var accessKeyId: String?,
    var secretAccessKey: String,
    var bucket: String,
    var multipartMinPartSize: Int = 5 * 1024 * 1024,
)
