package com.spring.batch.demo.config;

import com.spring.batch.demo.entity.User;
import com.spring.batch.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class userSpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private UserRepository userRepository;


    public FlatFileItemReader<User> reader(){
        FlatFileItemReader<User> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource("/src/main/resources/user_data.csv"));
        reader.setName("user-reader");
        reader.setLinesToSkip(1);
        reader.setLineMapper(linrMapper());
        return reader;
    }

    private LineMapper<User> linrMapper() {
        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setStrict(false);
        tokenizer.setNames("id", "firstName", "lastName", "email", "phNumber", "dob");

        BeanWrapperFieldSetMapper<User> userBeanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        userBeanWrapperFieldSetMapper.setTargetType(User.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(userBeanWrapperFieldSetMapper);

        return lineMapper;

    }

    @Bean
    public UserProcessor processor(){
        return new UserProcessor();
    }

    @Bean
    public RepositoryItemWriter<User>  writer(){
        RepositoryItemWriter repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(userRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;

    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("user-step").<User,User>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();

    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("user-job").flow(step1()).end().build();
    }

}
