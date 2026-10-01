"""
SMTP ao cho dev: lang nghe localhost:1025, moi email gui den duoc ghi ra
file fake_mail.txt (cung thu muc) va in ra console.
Chay:  python fake_smtp.py
Dung:  Ctrl+C
"""
import asyncio
import os
from datetime import datetime
from pathlib import Path

from aiosmtpd.controller import Controller

OUT_FILE = Path(__file__).with_name("fake_mail.txt")


class MailSink:
    async def handle_DATA(self, server, session, envelope):
        mail = (
            f"\n========== {datetime.now():%H:%M:%S} ==========\n"
            f"From: {envelope.mail_from}\n"
            f"To:   {', '.join(envelope.rcpt_tos)}\n"
            f"{envelope.content.decode('utf-8', errors='replace')}\n"
        )
        with open(OUT_FILE, "a", encoding="utf-8") as f:
            f.write(mail)
        print(mail.strip(), flush=True)
        return "250 Message accepted for delivery"


if __name__ == "__main__":
    controller = Controller(MailSink(), hostname="127.0.0.1", port=1025)
    controller.start()
    print(f"Fake SMTP dang chay tai 127.0.0.1:1025 - mail luu vao {OUT_FILE}")
    try:
        asyncio.get_event_loop().run_forever()
    except KeyboardInterrupt:
        controller.stop()
