package config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;

import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class SNSConfig {
    @Value("${aws.awsAccessKeyId:}")
    private  String accessKey;
    @Value("${aws.awsSecretAccessKey:}")
    private  String secretKey;

    @Primary
    @Bean
    public AmazonSNSClient getSNSClient(){
        return (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.accessKey
                        ,this.secretKey)))
                .build();
    }
}
