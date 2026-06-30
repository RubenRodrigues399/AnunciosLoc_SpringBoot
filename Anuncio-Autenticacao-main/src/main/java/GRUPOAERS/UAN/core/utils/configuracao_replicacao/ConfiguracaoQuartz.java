package GRUPOAERS.UAN.core.utils.configuracao_replicacao;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import GRUPOAERS.UAN.core.utils.configuracao_replicacao.operacores_replicacao.RegistarUtilizadorJob;

import org.springframework.context.ApplicationContext;

@Configuration
public class ConfiguracaoQuartz{

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(RegistarUtilizadorJob.class)
                .withIdentity("replicacaoUtilizadorJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //.withIntervalInMinutes(30)
                .withIntervalInSeconds(30)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("replicacaoTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

@Bean
public SchedulerFactoryBean schedulerFactoryBean(JobDetail jobDetail, Trigger trigger, ApplicationContext context) {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();

    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(context);

    factory.setJobFactory(jobFactory);
    factory.setJobDetails(jobDetail);
    factory.setTriggers(trigger);

    return factory;
}

}
