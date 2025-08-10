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
import { useState } from "react";
import { useRouter } from "next/navigation";
import { ArrowLeft } from "@deemlol/next-icons";
import { authenticate, isAuthenticated } from "@/utils/jwtUtils";
import { toast, Toaster } from "sonner"
import { keycloakApi } from "@/utils/api";


function alertError(title: string, description: string) {
  toast.error(title, {
          description: description,
          action: {
            label: "Ok",
            onClick: () => {},
          },
        })
}

export function LoginForm({ className, ...props }: React.ComponentProps<"div">) {
  const apiUrl = process.env.NEXT_PUBLIC_KEYCLOAK_URL;
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('');

  if (isAuthenticated()) {
    router.push('/');
  }

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true)

    try {
      const res = await keycloakApi.post(
        '/realms/Alexandre/protocol/openid-connect/token',
        {
          grant_type: 'password',
          client_id: 'public-client',
          username: email,
          password: password,
        }, 
        {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        }
      )

      const { access_token, refresh_token } = res.data
      authenticate(access_token, refresh_token)
      router.push('/');
      
    } catch (err) {
      if (err.response.status == 401) {
        alertError("invalide credentials", "The password or emails entered are invalid")
      } else {
        alertError("Error login", "Try again later, if the issue persist report it to us :>")
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

  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <Toaster/>
        <CardHeader>
          <Link href={"/"}>
            <ArrowLeft />
          </Link>
          <CardTitle>Login to your account</CardTitle>
          <CardDescription>
            Enter your email below to login to your account
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleLogin}>
            <div className="flex flex-col gap-6">
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
                  <Link
                    href="/forgot-password"
                    className="ml-auto inline-block text-sm underline-offset-4 hover:underline"
                  >
                    Forgot your password?
                  </Link>
                </div>
                <Input id="password" type="password" onChange={passwordChangeHandler} value={password} required />
              </div>
              <div className="flex flex-col gap-3">
                <Button type="submit" className="w-full" disabled={loading}>
                  { loading ? "Loading..." : "Login"}
                </Button>
              </div>
            </div>
            <div className="mt-4 text-center text-sm">
              Don&apos;t have an account?{" "}
              <Link href="/register" className="underline underline-offset-4">
                Sign up
              </Link>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
