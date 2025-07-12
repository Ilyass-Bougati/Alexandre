#!/usr/bin/python3

from NtfyPy.Notification import Notification, Ntfy
from sys import argv

# Checking if a topic was provided
if len(argv) != 4:
    print("Wrong usage of the script")
    print("usage : ")
    print("     ./notify.py [topic] [message] [tags]")
    exit(1)


TOPIC   = argv[1]
MESSAGE = argv[2]
TAGS    = argv[3]
ntfy    = Ntfy(TOPIC)

# Sending the notification
notification = Notification(
    message = MESSAGE,
    priority = "high",
    tags = TAGS,
    title = "Alexandre deployment"
)
ntfy.send(notification)
