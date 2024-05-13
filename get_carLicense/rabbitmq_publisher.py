import pika

def load(message):
    credentials = pika.PlainCredentials('ao666', '123456')  # mq用户名和密码
    # 虚拟队列需要指定参数 virtual_host，如果是默认的可以不填。
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host='8.130.128.161', port=5672, virtual_host='/', credentials=credentials))
    channel = connection.channel()
    # 声明消息队列，消息将在这个队列传递，如不存在，则创建
    queue_name = "car_license_queue"
    channel.queue_declare(queue=queue_name, durable=True)


    # 向队列插入数值 routing_key是队列名
    channel.basic_publish(exchange='', routing_key=queue_name, body=message)
    print(message)
    connection.close()
