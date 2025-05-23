package de.starwit.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.io.ByteArrayInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.starwit.aic.model.Module;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import jakarta.annotation.PostConstruct;

@Service
public class MinioService {

    Logger log = LoggerFactory.getLogger(MinioService.class);

    @Value("${minio.endpoint:http://localhost:9000}")
    private String minioURL;

    @Value("${minio.username:minioadmin}")
    private String username;

    @Value("${minio.password:minioadmin}")
    private String password;

    @Value("${minio.bucket:sboms}")
    private String sbomBucket;

    private MinioClient minioClient;

    @PostConstruct
    public void init() {
        minioClient = MinioClient.builder()
                .endpoint(minioURL)
                .credentials(username, password)
                .build();
        try {
            var buckets = minioClient.listBuckets();
            log.info("Connected to MinIO: " + minioURL);
            for (var bucket : buckets) {
                log.info("Bucket: " + bucket.name());
            }
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IOException e) {
            log.error("MinIO error " + e.getMessage());
        }

        checkAndCreateBucket();
    }

    public void checkAndCreateBucket() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(sbomBucket).build());
            if (found) {
                log.info("sbom bucket already exist " + sbomBucket);
            } else {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(sbomBucket).build());
            }
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            log.error("MinIO error " + e.getMessage());
        }
    }

    public void uploadFile(byte[] reportContent, Module m, String sbomName) {
        String filename = m.getName() + "_" + sbomName + "_" + m.getVersion() + ".json";
        uploadFile(reportContent, filename);
    }

    public void uploadFile(byte[] fileContent, String type, Module m) {
        String filename = m.getName() + "_" + m.getVersion() + ".";
        if (type.equals("pdf")) {
            filename += "pdf";
        } else if (type.equals("spreadsheet")) {
            filename += "xlsx";
        }

        uploadFile(fileContent, filename);
    }

    private void uploadFile(byte[] fileContent, String filename) {
        ByteArrayInputStream bais = new ByteArrayInputStream(fileContent);
        try {
            minioClient.putObject(
                PutObjectArgs.builder().bucket(sbomBucket).object(filename).stream(
                        bais, bais.available(), -1)
                    .build());
            bais.close();
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            log.error("MinIO error " + e.getMessage());
        }
    }

}
