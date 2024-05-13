from datetime import datetime
from wsgiref.handlers import format_date_time
from time import mktime
import hashlib
import base64
import hmac
from urllib.parse import urlencode
import json
import requests

'''
1、通用文字识别,图像数据base64编码后大小不得超过10M
2、appid、apiSecret、apiKey请到讯飞开放平台控制台获取并填写到此demo中
3、支持中英文,支持手写和印刷文字。
4、在倾斜文字上效果有提升，同时支持部分生僻字的识别
'''

APPId = "448cad51"  # 控制台获取
APISecret = "YzM0Y2Y1ZTk2NTU4NDZiOTc2ZjBiNTNi"  # 控制台获取
APIKey = "eafbd6e0b67cb57e0e21f13f6524c664"  # 控制台获取

def get2(img_url):

    # with open("1.jpg", "rb") as f:
    #     imageBytes = f.read()

    with open(img_url, "rb") as f:
        imageBytes = f.read()


    class AssembleHeaderException(Exception):
        def __init__(self, msg):
            self.message = msg


    class Url:
        def __init__(self, host, path, schema):
            self.host = host
            self.path = path
            self.schema = schema
            pass


    # calculate sha256 and encode to base64
    def sha256base64(data):
        sha256 = hashlib.sha256()
        sha256.update(data)
        digest = base64.b64encode(sha256.digest()).decode(encoding='utf-8')
        return digest


    def parse_url(requset_url):
        stidx = requset_url.index("://")
        host = requset_url[stidx + 3:]
        schema = requset_url[:stidx + 3]
        edidx = host.index("/")
        if edidx <= 0:
            raise AssembleHeaderException("invalid request url:" + requset_url)
        path = host[edidx:]
        host = host[:edidx]
        u = Url(host, path, schema)
        return u


    # build websocket auth request url
    def assemble_ws_auth_url(requset_url, method="POST", api_key="", api_secret=""):
        u = parse_url(requset_url)
        host = u.host
        path = u.path
        now = datetime.now()
        date = format_date_time(mktime(now.timetuple()))
        print(date)
        # date = "Thu, 12 Dec 2019 01:57:27 GMT"
        signature_origin = "host: {}\ndate: {}\n{} {} HTTP/1.1".format(host, date, method, path)
        print(signature_origin)
        signature_sha = hmac.new(api_secret.encode('utf-8'), signature_origin.encode('utf-8'),
                                 digestmod=hashlib.sha256).digest()
        signature_sha = base64.b64encode(signature_sha).decode(encoding='utf-8')
        authorization_origin = "api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"" % (
            api_key, "hmac-sha256", "host date request-line", signature_sha)
        authorization = base64.b64encode(authorization_origin.encode('utf-8')).decode(encoding='utf-8')
        print(authorization_origin)
        values = {
            "host": host,
            "date": date,
            "authorization": authorization
        }

        return requset_url + "?" + urlencode(values)


    url = 'https://api.xf-yun.com/v1/private/sf8e6aca1'

    body = {
        "header": {
            "app_id": APPId,
            "status": 3
        },
        "parameter": {
            "sf8e6aca1": {
                "category": "ch_en_public_cloud",
                "result": {
                    "encoding": "utf8",
                    "compress": "raw",
                    "format": "json"
                }
            }
        },
        "payload": {
            "sf8e6aca1_data_1": {
                "encoding": "jpg",
                "image": str(base64.b64encode(imageBytes), 'UTF-8'),
                "status": 3
            }
        }
    }

    request_url = assemble_ws_auth_url(url, "POST", APIKey, APISecret)

    headers = {'content-type': "application/json", 'host': 'api.xf-yun.com', 'app_id': APPId}
    print(request_url)
    response = requests.post(request_url, data=json.dumps(body), headers=headers)
    print(response)
    print(response.content)

    print("resp=>" + response.content.decode())
    tempResult = json.loads(response.content.decode())

    finalResult = base64.b64decode(tempResult['payload']['result']['text']).decode()
    finalResult = finalResult.replace(" ", "").replace("\n", "").replace("\t", "").strip()
    # print("text字段Base64解码后=>" + finalResult)
    str1 = finalResult.split('"content":')[1]
    str2 = str1[1:10]
    # 第一步：分割第一个字符char0和剩余字符串s1
    char0 = str2[0]
    s1 = str2[1:]

    # 第二步：过滤s1中不是数字和英语大小写字母的字符
    s1_filtered = ''.join(char for char in s1 if char.isalnum())

    # 第三步：将char0和过滤后的s1拼接为s
    s_final = char0 + s1_filtered

    print(s_final)
    return s_final