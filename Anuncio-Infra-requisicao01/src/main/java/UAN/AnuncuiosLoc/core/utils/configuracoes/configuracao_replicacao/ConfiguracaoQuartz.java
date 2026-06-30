package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao;

import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import org.springframework.context.ApplicationContext;

@Configuration
public class ConfiguracaoQuartz{

//     @Bean
//     public JobDetail jobRegistarAnuncioDetail() {
//         return JobBuilder.newJob(RegistarAnuncioJob.class)
//                 .withIdentity("replicacaoAnuncioJob")
//                 .storeDurably()
//                 .build();
//     }

//     @Bean
//     public Trigger jobRegistarAnuncioTrigger() {
//         SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                 .withIntervalInMinutes(2)
// //                .withIntervalInSeconds(50)
//                 .repeatForever();

//         return TriggerBuilder.newTrigger()
//                 .forJob(jobRegistarAnuncioDetail())
//                 .withIdentity("replicacaoAnuncioTrigger")
//                 .withSchedule(scheduleBuilder)
//                 .build();
//     }

    // @Bean
    // public JobDetail jobRegistarLeitorDetail() {
    //     return JobBuilder.newJob(RegistarLeitorJob.class)
    //             .withIdentity("replicacaoLeitorJob")
    //             .storeDurably()
    //             .build();
    // }

    // @Bean
    // public Trigger jobRegistarLeitorTrigger() {
    //     SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
    //             .withIntervalInMinutes(1)
    //             //.withIntervalInSeconds()
    //             .repeatForever();

    //     return TriggerBuilder.newTrigger()
    //             .forJob(jobRegistarLeitorDetail())
    //             .withIdentity("replicacaoLeitorTrigger")
    //             .withSchedule(scheduleBuilder)
    //             .build();
    // }


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
        ApplicationContext context,
        // injeta todos os beans JobDetail e Trigger
        List<JobDetail> jobDetails,
        List<Trigger> triggers
    ) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(context);
        factory.setJobFactory(jobFactory);
        // converte listas para arrays
        factory.setJobDetails(jobDetails.toArray(new JobDetail[0]));
        factory.setTriggers(triggers.toArray(new Trigger[0]));

        return factory;
    }

}
