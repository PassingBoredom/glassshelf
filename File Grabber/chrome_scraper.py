import requests
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service as ChromeService
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.firefox.options import Options as Options_FF
from webdriver_manager.firefox import GeckoDriverManager
import time
import os

URL = "https://www.cs.rutgers.edu/courses/314/classes/spring_2023_kremer/"
page = requests.get(URL)

def getDir(path):

    dir_list = os.listdir(path)
    dir_list = [f for f in dir_list if os.path.isfile(path+'/'+f)]

    return dir_list

def dl_check(name, folder):

    list = getDir(folder)

    if name in list:
        return

    time.sleep(5)
    dl_check(name, folder)

def download_pdf(lnk, here):

    download_folder = here

    profile = {"plugins.plugins_list": [{"enabled": False,
        "name": "Chrome PDF Viewer"}],
        "download.default_directory": download_folder,
        "download.extensions_to_open": "",
        "download.prompt_for_download": False,
        "download.directory_upgrade": True,
        "plugins.always_open_pdf_externally": True}


    print("Downloading file from link: {}".format(lnk))

    opts = Options()
    opts.add_argument("user-agent=[Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36]")
    opts.add_experimental_option("prefs",profile)    
    opts.add_argument("--headless=new")

    opts.add_experimental_option('excludeSwitches', ['enable-logging']) # option added that stops the device failure warning,
                                                                        # for my current use, I can ignore the warning and use as is, 
                                                                        # however, in the future I might need the specific device 
                                                                        # remove the line incase I need the feature 2023_04_09

    dl_driver = webdriver.Chrome(service=ChromeService(ChromeDriverManager().install()),options=opts)
    dl_driver.get(lnk)

    filename = lnk.split("/")[8]
    print("File: {}".format(filename))

    dl_check(filename, download_folder)

    print("Status: Download Complete.")
    print("Folder: {}".format(download_folder))

    dl_driver.close()

if __name__ == "__main__": 

    print("====================")
    print("Searching downloads for lectures and homework")

    opts = Options()
    opts.add_argument("user-agent=[Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36]")
    opts.add_argument("--headless=new")
    driver = webdriver.Chrome(service=ChromeService(ChromeDriverManager().install()),options=opts)
    driver.get(URL)


    soup = BeautifulSoup(page.content, "html.parser")
    ulists = soup.find_all("ul")

    lect = []
    hw = []
    preLink = 'https://www.cs.rutgers.edu/courses/314/classes/'

    for ul in ulists:
        for link in ul.findAll("a"):
            if("lectures" in link["href"]): 
                full_Link = preLink + link["href"]
                # print(full_Link)
                lect.append(full_Link)
            if("homework" in link["href"]):
                full_Link = preLink + link["href"]
                # print(full_Link)
                hw.append(full_Link)

    driver.close()

    installed_lect = getDir("/path/directory")
    installed_hw = getDir("/path/directory")

    for pdf in lect:
        filename = pdf.split("/")[8]
        if(filename not in installed_lect):
            download_pdf(pdf, "\\path\\directory")

    for work in hw: 
        filename = work.split("/")[8]
        if(filename not in installed_hw):
            download_pdf(work, "\\path\\directory")

    print("====================")
    print("Download(s) Complete")


