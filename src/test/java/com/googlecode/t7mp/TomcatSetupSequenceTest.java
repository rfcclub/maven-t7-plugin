package com.googlecode.t7mp;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.logging.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.googlecode.t7mp.steps.Context;
import com.googlecode.t7mp.steps.DefaultContext;
import com.googlecode.t7mp.steps.StepSequence;
import com.googlecode.t7mp.steps.tomcat.TomcatSetupSequence;

@Ignore
public class TomcatSetupSequenceTest {
    
    private final static String TOMCAT_VERSION = "7.0.14";
    
    private File catalinaBaseDir;
    private Context context;
    private AbstractT7Mojo mojo = Mockito.mock(AbstractT7Mojo.class);
    private Log log = new SysoutLog();
    
    private ArtifactResolver resolver = Mockito.mock(ArtifactResolver.class);
    private ArtifactFactory factory = Mockito.mock(ArtifactFactory.class);
    private ArtifactRepository local = Mockito.mock(ArtifactRepository.class);
    
    
    @Before
    public void setUp(){
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        catalinaBaseDir = new File(tempDir, UUID.randomUUID().toString());
        Mockito.when(mojo.getLog()).thenReturn(log);
        Mockito.when(mojo.getCatalinaBase()).thenReturn(catalinaBaseDir);
        Mockito.when(mojo.getTomcatVersion()).thenReturn(TOMCAT_VERSION);
        // 
        context = new DefaultContext(mojo);
    }
    
    @After
    public void tearDown() throws IOException{
        FileUtils.deleteDirectory(catalinaBaseDir);
    }
    
    @Test
    public void testTomcatSetupSequence(){
        StepSequence setupSequence = new TomcatSetupSequence();
        setupSequence.execute(context);
    }

}
