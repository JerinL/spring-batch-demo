//package com.spring.batch.demo.config;
//
//import com.spring.batch.demo.entity.Customer;
//import com.spring.batch.demo.repository.CustomerRepository;
//import com.spring.batch.demo.repository.UserRepository;
//import com.sun.jdi.connect.LaunchingConnector;
//import lombok.AllArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.LineMapper;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//
//@Configuration
//@EnableBatchProcessing
//@AllArgsConstructor
//public class SpringBatchConfig {
//
//
//    private JobBuilderFactory jobBuilderFactory;
//    private StepBuilderFactory stepBuilderFactory;
//    private UserRepository userRepository;
//    private CustomerRepository customerRepository;
//
//    //Item reader
//    @Bean
//    public FlatFileItemReader<Customer> reader() {
//        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
//        reader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
//        reader.setName("itemReader");
//        reader.setLinesToSkip(1);
//        reader.setLineMapper(linemapper());
//        return reader;
//    }
//
//    private LineMapper<Customer> linemapper() {
//        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper();
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); // lineTokenizer used for extract the value from csv file
//        lineTokenizer.setDelimiter(",");
//        lineTokenizer.setStrict(false);
//        lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");
//
//        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();  // fieldSetMapper map that value to the target class
//        fieldSetMapper.setTargetType(Customer.class);
//
//        lineMapper.setLineTokenizer(lineTokenizer);
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//        return lineMapper;
//    }
//
//    //Item processor
//    @Bean
//    public UserProcessor userProcessor(){
//        return new UserProcessor();
//    }
//
//    // Item writer
//    //save the data to the database
//    @Bean
//    public RepositoryItemWriter<Customer> writer(){
//        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
//        writer.setRepository(customerRepository);
//        writer.setMethodName("save");
//        return writer;
//        // we telling  writer to use the my user repository and do call the save method to save  the data to my database
//    }
//
//    @Bean
//    public Step step1(){
//        return stepBuilderFactory.get("csv-step").<Customer,Customer>chunk(10)
//                .reader(reader())
//                .processor(userProcessor())
//                .writer(writer())
//                .build();
//    }
//
//    @Bean
//    public Job runJob(){
//        return jobBuilderFactory.get("importUser")
//                .flow(step1()).end().build();
//    }
//
//}
