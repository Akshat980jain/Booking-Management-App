-- Migration: Add Admin INSERT policy to appointments
-- To allow Admins to create bookings on behalf of users or themselves.

DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM pg_policies 
    WHERE tablename = 'appointments' AND policyname = 'Admins can insert any appointment'
  ) THEN
    CREATE POLICY "Admins can insert any appointment" 
      ON public.appointments FOR INSERT 
      WITH CHECK (has_role(auth.uid(), 'admin'::app_role));
  END IF;
END $$;
