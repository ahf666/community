import cv2
from matplotlib import pyplot as plt

path = "img1.jpg"
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
plt.imshow(image1)
plt.show()

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
        get_car_license.img = image2
        plt.imshow(image2)
        plt.show()
