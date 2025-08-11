'use client';

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import Link from "next/link"
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { ArrowLeft } from "@deemlol/next-icons";
import { Toaster, toast } from "sonner"
import { api } from "@/utils/api"
import { AxiosError } from "axios"
import axios from "axios";

const PhoneNumberRegex = /^[0-9]*$/;
const NameRegex = /^[a-zA-Z]*$/

function alertError(title: string, description: string) {
  toast.error(title, {
          description: description,
          action: {
            label: "Ok",
            onClick: () => {},
          },
        })
}


export function RegisterForm({ className, ...props }: React.ComponentProps<"div">) {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [loading, setLoading] = useState(false)
  const [password, setPassword] = useState('');

  useEffect(() => {
    axios.get("/api/auth")
      .then((res) => {
          if (res.status == 200) {
            router.push('/');
          }
      })
  })

  const handleLogin = async (e: React.FormEvent) => {
    setLoading(true)
    e.preventDefault();

    try {
      await api.post(
        '/auth/api/v1/register/', 
        {
          email: email,
          password: password,
          firstName: firstName,
          lastName: lastName,
          phoneNumber: phoneNumber
        },
        {
        headers: { 'Content-Type': 'application/json' },
        }
      );

      router.push('/login');
    } catch (err) {
      if (err instanceof AxiosError) {
        if (err.response?.status === 401) {
          alertError("Invalid credentials", "The password or email entered are invalid");
        } else {
          alertError("Error login", "Try again later, if the issue persists report it to us :>");
        }
      } else {
        alertError("Error login", "Try again later, if the issue persists report it to us :>");
      }
    }
    
    setLoading(false)
  };

  const emailChangeHandler = (e: React.ChangeEvent<HTMLInputElement >) => {
    setEmail(e.target.value)
  }

  const passwordChangeHandler = (e: React.ChangeEvent<HTMLInputElement >) => {
    setPassword(e.target.value)
  }

  const firstNameChangeHandler = (e: React.ChangeEvent<HTMLInputElement >) => {
    const value = e.target.value
    if (NameRegex.test(value)) {
      if (value.length == 1) {
        setFirstName(value.toUpperCase())
      } else {
        setFirstName(value)
      }
    }
  }

  const lastNameChangeHandler = (e: React.ChangeEvent<HTMLInputElement >) => {
    const value = e.target.value
    if (NameRegex.test(value)) {
      if (value.length == 1) {
        setLastName(value.toUpperCase())
      } else {
        setLastName(value)
      }
    }
  }

  const phoneNumberChangeHandler = (e: React.ChangeEvent<HTMLInputElement >) => {
    if (PhoneNumberRegex.test(e.target.value)) {
      setPhoneNumber(e.target.value)
    }
  }


  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <Toaster />
        <CardHeader>
          <Link href={"/"}>
            <ArrowLeft />
          </Link>
          <CardTitle>Create a new account</CardTitle>
          <CardDescription>
            Fill in your informations to create a new account
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleLogin}>
            <div className="flex flex-col gap-6">
              <div className="grid grid-cols-2 gap-4">
                <div className="flex flex-col gap-1">
                  <Label htmlFor="first-name">First name</Label>
                  <Input
                    id="first-name"
                    type="text"
                    placeholder="First name"
                    onChange={firstNameChangeHandler}
                    value={firstName}
                    required
                  />
                </div>

                <div className="flex flex-col gap-1">
                  <Label htmlFor="last-name">Last name</Label>
                  <Input
                    id="last-name"
                    type="text"
                    placeholder="Last name"
                    onChange={lastNameChangeHandler}
                    value={lastName}
                    required
                  />
                </div>
              </div>
              <div className="grid gap-3">
                <Label htmlFor="phone">Phone</Label>
                <Input
                  id="phone"
                  type="text"
                  placeholder="Phone number"
                  onChange={phoneNumberChangeHandler}
                  value={phoneNumber}
                  required
                />
              </div>
              <div className="grid gap-3">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="m@example.com"
                  onChange={emailChangeHandler}
                  value={email}
                  required
                />
              </div>
              <div className="grid gap-3">
                <div className="flex items-center">
                  <Label htmlFor="password">Password</Label>
                </div>
                <Input id="password" type="password" onChange={passwordChangeHandler} value={password} required />
              </div>
              <div className="flex flex-col gap-3">
                <Button type="submit" className="w-full" disabled={loading}>
                  { loading ? "Loading..." : "Register"}
                </Button>
              </div>
            </div>
            <div className="mt-4 text-center text-sm">
              Already have an account?{" "}
              <Link href="/login" className="underline underline-offset-4">
                Login
              </Link>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
