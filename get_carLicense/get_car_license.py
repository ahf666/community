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

'''
1、通用文字识别,图像数据base64编码后大小不得超过10M
2、appid、apiSecret、apiKey请到讯飞开放平台控制台获取并填写到此demo中
3、支持中英文,支持手写和印刷文字。
4、在倾斜文字上效果有提升，同时支持部分生僻字的识别
'''

APPId = "448cad51"  # 控制台获取
APISecret = "xxxx"  # 控制台获取
APIKey = "xxxx"  # 控制台获取

#########################################

path = "img2.jpg"
rawImage = cv2.imread(path)

# 高斯去噪
image = cv2.GaussianBlur(rawImage, (3, 3), 0)
# plt.figure("image")
# plt.imshow(image)
# plt.show()

# 灰度处理
gray_image = cv2.cvtColor(image, cv2.COLOR_RGB2GRAY)

# sobel算子边缘检测(车牌是突出的)
Sobel_x = cv2.Sobel(gray_image, cv2.CV_16S, 1, 0)
Sobel_y = cv2.Sobel(gray_image, cv2.CV_16S, 0, 1)
absX = cv2.convertScaleAbs(Sobel_x)
absY = cv2.convertScaleAbs(Sobel_y)
dst = cv2.addWeighted(absX, 0.5, absY, 0.5, 0)

image = dst
# plt.figure("dst")
# plt.imshow(image)
# plt.show()

# 自适应阈值处理(自适应的只转化为黑白两种颜色，方便提取边缘)
ret, image = cv2.threshold(image, 0, 255, cv2.THRESH_OTSU)
# plt.imshow(image)
# plt.show()

# 闭运算，白色部分连成整体,膨胀，x方向更大
Kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (17, 5))
print(Kernel)
image = cv2.morphologyEx(image, cv2.MORPH_CLOSE, Kernel, iterations = 3)
# plt.imshow(image)
# plt.show()

# 去除一些小的白点
kernelX = cv2.getStructuringElement(cv2.MORPH_RECT, (20, 1))
kernelY = cv2.getStructuringElement(cv2.MORPH_RECT, (1, 19))

# 膨胀，腐蚀
image = cv2.dilate(image, kernelX)
image = cv2.erode(image, kernelX)

image = cv2.dilate(image, kernelY)
image = cv2.erode(image, kernelY)
# plt.imshow(image)
# plt.show()

# 中值滤波
image = cv2.medianBlur(image, 15)
# plt.imshow(image)
# plt.show()

# 轮廓检测
# cv2.RETR_EXTERNAL参数-检测外轮廓
# CHAIN_APPROX_SIMPLE参数-压缩垂直与水平方向的坐标
contours, hierarchy = cv2.findContours(image, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

# 绘制轮廓
image1 = rawImage.copy()
cv2.drawContours(image1, contours, -1, (0, 255, 0), 5)
# plt.imshow(image1)
# plt.show()

# 筛选出车牌的位置轮廓, 长宽比在3：1到4：1之间的矩形筛选
for item in contours:
    # cv2.boundingRect用一个最小的矩形，把找到的形状包起来
    rect = cv2.boundingRect(item)
    x = rect[0]
    y = rect[1]
    weight = rect[2]
    height = rect[3]

    if(weight > height*2) and (weight < (height * 4)):
        image2 = rawImage[y:y+height, x:x+weight]
        imageBytes = image2
        # 如果imageBytes不是C连续的，你可以先复制它
        imageBytes = imageBytes.copy(order='C')

        ##########################################

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

        list1 = finalResult.split('content')
        list2 = []
        list3 = []
        if len(list1) > 1:
            list2 = list1[1].split(',')
            print(list2)
            if len(list2) > 1:
                list3 = list2[0].split('"')
                print(list3[2])
        else:
            # 处理list1元素不足的情况，例如：
            print("list1中元素不足，无法访问第二个元素")
        print("---------------------------------")

        # list1 = finalResult.split('content')
        # list2 = list1[1].split(',')
        # print(list2)
        # list3 = list2[0].split('"')
        # print(list3[2])

        ##########################################



        # plt.imshow(image2)
        # plt.show()


#######################################



# class img():
#     def __int__(self, img):
#         self.img = img
######
#
# with open("5.png", "rb") as f:
#     imageBytes = f.read()


# class AssembleHeaderException(Exception):
#     def __init__(self, msg):
#         self.message = msg
#
#
# class Url:
#     def __init__(self, host, path, schema):
#         self.host = host
#         self.path = path
#         self.schema = schema
#         pass
#
#
# # calculate sha256 and encode to base64
# def sha256base64(data):
#     sha256 = hashlib.sha256()
#     sha256.update(data)
#     digest = base64.b64encode(sha256.digest()).decode(encoding='utf-8')
#     return digest
#
#
# def parse_url(requset_url):
#     stidx = requset_url.index("://")
#     host = requset_url[stidx + 3:]
#     schema = requset_url[:stidx + 3]
#     edidx = host.index("/")
#     if edidx <= 0:
#         raise AssembleHeaderException("invalid request url:" + requset_url)
#     path = host[edidx:]
#     host = host[:edidx]
#     u = Url(host, path, schema)
#     return u
#
#
# # build websocket auth request url
# def assemble_ws_auth_url(requset_url, method="POST", api_key="", api_secret=""):
#     u = parse_url(requset_url)
#     host = u.host
#     path = u.path
#     now = datetime.now()
#     date = format_date_time(mktime(now.timetuple()))
#     print(date)
#     # date = "Thu, 12 Dec 2019 01:57:27 GMT"
#     signature_origin = "host: {}\ndate: {}\n{} {} HTTP/1.1".format(host, date, method, path)
#     print(signature_origin)
#     signature_sha = hmac.new(api_secret.encode('utf-8'), signature_origin.encode('utf-8'),
#                              digestmod=hashlib.sha256).digest()
#     signature_sha = base64.b64encode(signature_sha).decode(encoding='utf-8')
#     authorization_origin = "api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"" % (
#         api_key, "hmac-sha256", "host date request-line", signature_sha)
#     authorization = base64.b64encode(authorization_origin.encode('utf-8')).decode(encoding='utf-8')
#     print(authorization_origin)
#     values = {
#         "host": host,
#         "date": date,
#         "authorization": authorization
#     }
#
#     return requset_url + "?" + urlencode(values)
#
#
# url = 'https://api.xf-yun.com/v1/private/sf8e6aca1'
#
# body = {
#     "header": {
#         "app_id": APPId,
#         "status": 3
#     },
#     "parameter": {
#         "sf8e6aca1": {
#             "category": "ch_en_public_cloud",
#             "result": {
#                 "encoding": "utf8",
#                 "compress": "raw",
#                 "format": "json"
#             }
#         }
#     },
#     "payload": {
#         "sf8e6aca1_data_1": {
#             "encoding": "jpg",
#             "image": str(base64.b64encode(imageBytes), 'UTF-8'),
#             "status": 3
#         }
#     }
# }
#
# request_url = assemble_ws_auth_url(url, "POST", APIKey, APISecret)
#
# headers = {'content-type': "application/json", 'host': 'api.xf-yun.com', 'app_id': APPId}
# print(request_url)
# response = requests.post(request_url, data=json.dumps(body), headers=headers)
# print(response)
# print(response.content)
#
# print("resp=>" + response.content.decode())
# tempResult = json.loads(response.content.decode())
#
# finalResult = base64.b64decode(tempResult['payload']['result']['text']).decode()
# finalResult = finalResult.replace(" ", "").replace("\n", "").replace("\t", "").strip()
# print("text字段Base64解码后=>" + finalResult)
# list1 = finalResult.split('content')
# list2 = list1[1].split(',')
# print(list2)
# list3 = list2[0].split('"')
# print(list3[2])
