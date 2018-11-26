package com.vma.app.kafka;

import com.vma.assist.wraps.LogWrap;
import org.slf4j.Logger;

/**
 * kafka消费者
 *
 * @author huang
 * @create 2018-07-25 19:39
 **/
//@Component
public class KafkaReceiver {

    private static final Logger LOG = LogWrap.getLogger(KafkaReceiver.class);

    /**
     * @param record record
     */
    //@KafkaListener(id = "kediantong_1",topicPartitions = @TopicPartition(topic = "kediantong_dev",partitions = {"0","1"}))
    /*public void listen(ConsumerRecord<?, ?> record) {
        LOG.info("kediantong  监听到新消息");

        //for (ConsumerRecord<?, ?> record:records){
        LOG.info("kediantong " + Thread.currentThread().getId() + " partition:" + record.partition() + "  offset:" + record.offset());
        //}
        System.out.println("end---------------------");
    }*/


}
