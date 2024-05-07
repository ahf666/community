from datetime import datetime
from pprint import pprint
from wsgiref.handlers import format_date_time
from time import mktime
import hashlib
import base64
import hmac
from urllib.parse import urlencode
import json
import requests
import cv2
from matplotlib import pyplot as plt

APPId = "448cad51"  # 控制台获取
APISecret = "YzM0Y2Y1ZTk2NTU4NDZiOTc2ZjBiNTNi"  # 控制台获取
APIKey = "eafbd6e0b67cb57e0e21f13f6524c664"  # 控制台获取

with open("5.png", "rb") as f:
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
print("text字段Base64解码后=>" + finalResult)

# list1 = finalResult.split('content')
# list2 = []
# list3 = []
# if len(list1) > 1:
#     list2 = list1[1].split(',')
#     print(list2)
#     if len(list2) > 1:
#         list3 = list2[0].split('"')
#         print(list3[2])
# else:
#     # 处理list1元素不足的情况，例如：
#     print("list1中元素不足，无法访问第二个元素")
# print("---------------------------------")

list1 = finalResult.split('content')
list2 = list1[1].split(',')
print(list2)
list3 = list2[0].split('"')
print(list3[2])