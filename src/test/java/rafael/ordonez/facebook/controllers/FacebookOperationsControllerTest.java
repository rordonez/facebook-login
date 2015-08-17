package rafael.ordonez.facebook.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.social.facebook.api.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rafael.ordonez.facebook.FacebookLoginApplication;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by rafa on 17/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {FacebookLoginApplication.class})
@WebAppConfiguration
public class FacebookOperationsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private Facebook facebook;

    @Mock
    private MediaOperations mediaOperations;

    @InjectMocks
    private FacebookOperationsController facebookOperationsController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .failOnEmptyBeans(false);

        mockMvc = MockMvcBuilders.standaloneSetup(facebookOperationsController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(builder.build()), new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build()))
                .build();

        Mockito.when(facebook.mediaOperations()).thenReturn(mediaOperations);
        Mockito.when(mediaOperations.getAlbums()).thenReturn(new PagedList<Album>(Arrays.asList(new Album()), null, null));
        Mockito.when(mediaOperations.getPhotos(Mockito.anyString())).thenReturn(new PagedList<Photo>(Arrays.asList(new Photo()), null, null));
    }

    @Test
    public void aGetRequestShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/facebook"))
                .andExpect(status().isOk());
    }

    @Test
    public void aGetRequestShouldReturnAListOk() throws Exception {
        mockMvc.perform(get("/api/facebook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void aGetRequestShouldReturnAListofPhotos() throws Exception {
        mockMvc.perform(get("/api/facebook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photos").isArray());
    }

    @Test
    public void aGetRequestShouldReturnAllThePhotosOfTheFirstFacebookAlbum() throws Exception {

        //When
        mockMvc.perform(get("/api/facebook.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(nullValue()))
                .andExpect(jsonPath("$.description").value(nullValue()))
                .andExpect(jsonPath("$.photos").isArray());
    }

    @Test
    public void aGetRequestShouldCallAFacebookApi() throws Exception {

        //When
        mockMvc.perform(get("/api/facebook"))
                .andExpect(status().isOk());

        Mockito.verify(facebook, Mockito.times(2)).mediaOperations();
    }

}