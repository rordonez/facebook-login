package rafael.ordonez.facebook.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rafael.ordonez.facebook.FacebookLoginApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 * Created by rafa on 17/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FacebookLoginApplication.class)
@WebAppConfiguration
public class FacebookConnectionControllerTest {

    @Mock
    private ConnectionRepository connectionRepository;

    @InjectMocks
    private FacebookConnectionController facebookConnectionController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(facebookConnectionController).build();
    }

    @Test
    public void whenNoneUserIsConnectedThenShouldRedirectToFacebookConnectView() throws Exception {
        Mockito.when(connectionRepository.findPrimaryConnection(Mockito.any())).thenReturn(null);

        this.mockMvc.perform(get("/"))
                .andExpect(redirectedUrl("/connect/facebook"));

    }

    @Test
    public void whenAUserHasAConnectionThenShouldRedirectToTheHomeController() throws Exception {
        Mockito.when(connectionRepository.findPrimaryConnection(Mockito.any())).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory("clientId", "clientSecret");
                Connection<Facebook> connection = connectionFactory.createConnection(new ConnectionData("facebook", "738140579", "", "", "", "", "", "", null));
                return connection;
            }
        });

        this.mockMvc.perform(get("/"))
                .andExpect(redirectedUrl("/api"));
    }
}
