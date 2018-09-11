package com.yj.mq;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * 功能描述：订单生成者 创建人：Administrator 创建时间：2018年9月1日 下午2:43:07
 * 
 * @version
 *
 */
@Component
public class OrderProducer {
	/**
	 * 生产者的组名
	 */
	@Value("${apache.rocketmq.producer.producerGroup}")
	private String producerGroup;
	/**
	 * NameServer 地址
	 */
	@Value("${apache.rocketmq.namesrvAddr}")
	private String namesrvAddr;

	private DefaultMQProducer producer;

	public DefaultMQProducer getProducer() {
		return this.producer;
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	public void defaultMQProducer() {
		// 生产者的组名
		producer = new DefaultMQProducer(producerGroup);
		// 指定NameServer地址，多个地址以 ; 隔开
		// 如
		// producer.setNamesrvAddr("192.168.100.141:9876;192.168.100.142:9876;192.168.100.149:9876");
		producer.setNamesrvAddr(namesrvAddr);
		producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");// 4.2.0以后需要加这个
															// 老版本客户端传递的默认AUTO_CREATE_TOPIC_KEY_TOPIC是“TBW102”
		producer.setVipChannelEnabled(false);
		try {
			/**
			 * Producer对象在使用之前必须要调用start初始化，只能初始化一次
			 */
			producer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// producer.shutdown(); 一般在应用上下文，关闭的时候进行关闭，用上下文监听器
	}
}
