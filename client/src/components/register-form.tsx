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


export function RegisterForm({ className, ...props }: React.ComponentProps<"div">) {

  const apiUrl = process.env.NEXT_PUBLIC_API_URL;
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');



  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    const res = await fetch(apiUrl + '/realms/Alexandre/protocol/openid-connect/token', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({
        grant_type: 'password',
        client_id: 'public-client',
        username: email,
        password: password,
      }),
    });

    if (!res.ok) {
      setError('Invalid credentials');
      return;
    }

    const { access_token } = await res.json();

    // Save JWT (choose cookie for SSR or localStorage for CSR)
    localStorage.setItem('jwt', access_token); // Or use a secure cookie (see below)

    router.push('/'); // Redirect to a protected route
  };

  const emailChangeHandler = (e: React.ChangeEvent<HTMLInputElement >) => {
    setEmail(e.target.value)
  }

  const passwordChangeHandler = (e: React.ChangeEvent<HTMLInputElement >) => {
    setPassword(e.target.value)
  }
  const firstNameChangeHandler = (e: React.ChangeEvent<HTMLInputElement >) => {
    setFirstName(e.target.value)
  }
  const lastNameChangeHandler = (e: React.ChangeEvent<HTMLInputElement >) => {
    setLastName(e.target.value)
  }
  const phoneNumberChangeHandler = (e: React.ChangeEvent<HTMLInputElement >) => {
    setPhoneNumber(e.target.value)
  }


  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
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
                <div className="grid gap-3">
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
              <div className="grid gap-3">
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
                <Button type="submit" className="w-full">
                  Register
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
