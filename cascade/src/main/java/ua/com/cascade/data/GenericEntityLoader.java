package ua.com.cascade.data;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class GenericEntityLoader<E> {

    public Collection<E> loadEntities(File fileToLoad, FieldSetMapper<E> fieldSetMapper, int linesToSkip, String fieldsDelimiter){
        Collection<E> entities = new ArrayList<E>();
        FlatFileItemReader<E> itemReader = new FlatFileItemReader<E>();
        itemReader.setResource(new FileSystemResource(fileToLoad.getAbsolutePath()));
        itemReader.setLinesToSkip(linesToSkip);
        //DelimitedLineTokenizer defaults to comma as its delimiter
        DefaultLineMapper<E> lineMapper = new DefaultLineMapper<E>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer(fieldsDelimiter));
        lineMapper.setFieldSetMapper(fieldSetMapper);
        itemReader.setLineMapper(lineMapper);
        itemReader.open(new ExecutionContext());
        try {
            E entity;
            while ((entity = itemReader.read()) != null){
                entities.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;

    }

}
