/*
package jd.demo.lib.office.uno;

@Configuration
@ConditionalOnClass({DocumentConverter.class})
@ConditionalOnProperty(
        prefix = "jodconverter",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = false
)
@EnableConfigurationProperties({JodConverterProperties.class})
public class JodConverterAutoConfiguration {
    private final JodConverterProperties properties;

    public JodConverterAutoConfiguration(JodConverterProperties properties) {
        this.properties = properties;
    }

    private OfficeManager createOfficeManager() {
        Builder builder = LocalOfficeManager.builder();
        if (!StringUtils.isBlank(this.properties.getPortNumbers())) {
            Set<Integer> iports = new HashSet();
            String[] var3 = StringUtils.split(this.properties.getPortNumbers(), ", ");
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String portNumber = var3[var5];
                iports.add(NumberUtils.toInt(portNumber, 2002));
            }

            builder.portNumbers(ArrayUtils.toPrimitive((Integer[])iports.toArray(new Integer[iports.size()])));
        }

        builder.officeHome(this.properties.getOfficeHome());
        builder.workingDir(this.properties.getWorkingDir());
        builder.templateProfileDir(this.properties.getTemplateProfileDir());
        builder.killExistingProcess(this.properties.isKillExistingProcess());
        builder.processTimeout(this.properties.getProcessTimeout());
        builder.processRetryInterval(this.properties.getProcessRetryInterval());
        builder.taskExecutionTimeout(this.properties.getTaskExecutionTimeout());
        builder.maxTasksPerProcess(this.properties.getMaxTasksPerProcess());
        builder.taskQueueTimeout(this.properties.getTaskQueueTimeout());
        return builder.build();
    }

    @Bean(
            initMethod = "start",
            destroyMethod = "stop"
    )
    @ConditionalOnMissingBean
    public OfficeManager officeManager() {
        return this.createOfficeManager();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({OfficeManager.class})
    public DocumentConverter jodConverter(OfficeManager officeManager) {
        return LocalConverter.make(officeManager);
    }
}

@ConfigurationProperties("jodconverter")
class JodConverterProperties {
    private boolean enabled;
    private String officeHome;
    private String portNumbers = "2002";
    private String workingDir;
    private String templateProfileDir;
    private boolean killExistingProcess = true;
    private long processTimeout = 120000L;
    private long processRetryInterval = 250L;
    private long taskExecutionTimeout = 120000L;
    private int maxTasksPerProcess = 200;
    private long taskQueueTimeout = 30000L;

    public JodConverterProperties() {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getOfficeHome() {
        return this.officeHome;
    }

    public void setOfficeHome(String officeHome) {
        this.officeHome = officeHome;
    }

    public String getPortNumbers() {
        return this.portNumbers;
    }

    public void setPortNumbers(String portNumbers) {
        this.portNumbers = portNumbers;
    }

    public String getWorkingDir() {
        return this.workingDir;
    }

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    public String getTemplateProfileDir() {
        return this.templateProfileDir;
    }

    public void setTemplateProfileDir(String templateProfileDir) {
        this.templateProfileDir = templateProfileDir;
    }

    public boolean isKillExistingProcess() {
        return this.killExistingProcess;
    }

    public void setKillExistingProcess(boolean killExistingProcess) {
        this.killExistingProcess = killExistingProcess;
    }

    public long getProcessTimeout() {
        return this.processTimeout;
    }

    public void setProcessTimeout(long processTimeout) {
        this.processTimeout = processTimeout;
    }

    public long getProcessRetryInterval() {
        return this.processRetryInterval;
    }

    public void setProcessRetryInterval(long procesRetryInterval) {
        this.processRetryInterval = procesRetryInterval;
    }

    public long getTaskExecutionTimeout() {
        return this.taskExecutionTimeout;
    }

    public void setTaskExecutionTimeout(long taskExecutionTimeout) {
        this.taskExecutionTimeout = taskExecutionTimeout;
    }

    public int getMaxTasksPerProcess() {
        return this.maxTasksPerProcess;
    }

    public void setMaxTasksPerProcess(int maxTasksPerProcess) {
        this.maxTasksPerProcess = maxTasksPerProcess;
    }

    public long getTaskQueueTimeout() {
        return this.taskQueueTimeout;
    }

    public void setTaskQueueTimeout(long taskQueueTimeout) {
        this.taskQueueTimeout = taskQueueTimeout;
    }
}
*/
