#!/usr/bin/python3

from NtfyPy.Notification import Notification, Ntfy
from sys import argv

# Checking if a topic was provided
if len(argv) != 2:
    print("Wrong usage of the script")
    print("usage : ")
    print("     ./notify.py [topic]")
    exit(1)


TOPIC = argv[1]
ntfy = Ntfy(TOPIC)

# Sending the notification
notification = Notification(
    message = "Error building the images",
    priority = "high",
    tags = "rotating_light",
    title = "Alexandre"
)
ntfy.send(notification)
