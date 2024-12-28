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
from selenium.webdriver.firefox.service import Service 
import time
import os

URL = "https://www.cs.rutgers.edu/courses/314/classes/spring_2023_kremer/"
page = requests.get(URL)
LOCAL_LECT = r"path\directory"
LOCAL_HW = r"path\directory"

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

    opts = Options_FF()
    opts.set_preference('browser.download.folderList', 2)
    opts.set_preference('browser.download.manager.showWhenStarting', False)
    opts.set_preference('browser.download.useDownloadDir', True)
    opts.set_preference('browser.download.dir', download_folder)
    opts.set_preference('browser.helperApps.neverAsk.saveToDisk', 'application/pdf')
    opts.set_preference('pdfjs.disabled', True)
    opts.set_preference('browser.download.viewableInternally.enabledTypes', '')
    dl_driver = webdriver.Firefox(service=Service(GeckoDriverManager().install()), options=opts)
  
    print("Downloading file from link: {}".format(lnk))
    dl_driver.get(lnk) # Blocking, what a pain

    filename = lnk.split("/")[8]
    print("File: {}".format(filename))

    # recursive loop to wait for file download
    dl_check(filename, download_folder)

    print("Status: Download Complete.")
    print("Folder: {}".format(download_folder))

    dl_driver.close()

if __name__ == "__main__": 

    print("====================")
    print("Searching downloads for lectures and homework")

    # Start 1 WebDriver to scrape all links on the site
    opts = Options_FF()
    # opts.add_argument("-headless")
    driver = webdriver.Firefox(service=Service(GeckoDriverManager().install()), options=opts)
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

    installed_lect = getDir(LOCAL_LECT)
    installed_hw = getDir(LOCAL_HW)

    # Start 2nd WebDriver to download from list of links
    for pdf in lect:
        filename = pdf.split("/")[8]
        if(filename not in installed_lect):
            download_pdf(pdf, LOCAL_LECT)

    for work in hw: 
        filename = work.split("/")[8]
        if(filename not in installed_hw):
            download_pdf(work, LOCAL_HW)

    print("====================")
    print("Download(s) Complete")


